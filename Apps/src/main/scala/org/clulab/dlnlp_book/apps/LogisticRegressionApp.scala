package org.clulab.dlnlp_book.apps

import me.shadaj.scalapy.numpy.NDArray
import me.shadaj.scalapy.numpy.NumPy
import me.shadaj.scalapy.interpreter.CPythonInterpreter
import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.{ObjectReader, SeqConverters}

class Python {
  val pyLen = py.Dynamic.global.len
  val pyList = py.Dynamic.global.list
  val pyDict = py.Dynamic.global.dict
  val pyFloat = py.Dynamic.global.float

  type PyAny = py.Any
}

object LogisticRegressionApp extends Python with App {
  val baseDir = "e:/DocumentCollections/aclImdb-short/"

  // In [1]:
  val random = py.module("random")
  val np = py.module("numpy") // .as[NumPy]
  val torch = py.module("torch")
  val tqdm = py.module("tqdm") // .notebook")

  // set this variable to a number to be used as the random seed
  // or to None if you don't want to set a random seed
  val seedOpt = Some(1234) // None
  seedOpt.foreach { seed =>
    random.seed(seed)
    np.random.seed(seed)
  }

  // In [4]:
  val glob = py.module("glob")
  var posFiles = glob.glob(baseDir + "train/pos/*.txt")
  var negFiles = glob.glob(baseDir + "train/neg/*.txt")

  println(s"number of positive reviews: ${pyLen(posFiles)}")
  println(s"number of negative reviews: ${pyLen(negFiles)}")

  // In [5]:
  val sklearn = py.module("sklearn.feature_extraction.text")
  val cv = sklearn.CountVectorizer(input = "filename")
  var docTermMatrix = cv.fit_transform(posFiles + negFiles)
  // println(docTermMatrix)

  // In [6]:
  var xTrain = docTermMatrix.toarray()
  println(xTrain.shape)

  // In [7]:
  // training labels
  var yPos = np.ones(pyLen(posFiles))
  var yNeg = np.zeros(pyLen(negFiles))
  var yTrain = np.concatenate(pyList(Seq(yPos, yNeg).toPythonProxy))
  println(yTrain)

  // In [8]:
  val Seq(nExamples, nFeatures) = xTrain.shape.as[Seq[Int]]

  // In [9]:
  val lr = 1e-1f
  val nEpochs = 10

  val model = torch.nn.Linear(nFeatures, 1)
  val lossFunc = torch.nn.BCEWithLogitsLoss()
  val optimizer = torch.optim.SGD(model.parameters(), lr = lr)

  xTrain = torch.tensor(xTrain, dtype = torch.float32)
  yTrain = torch.tensor(yTrain, dtype = torch.float32)

  val indices = np.arange(nExamples)
  Range(0, nEpochs).foreach { epoch =>
    val nErrors = 0
    // randomize training examplesa
    np.random.shuffle(indices)
    // for each training exmaple
    val progressBar = tqdm.tqdm(indices, desc = s"epoch ${epoch + 1}") // Get some kind of range?
    Range(0, nExamples).foreach { i =>
      val x = xTrain.bracketAccess(i)
      val yTrue = yTrain.bracketAccess(i)
      // make predictions
      val yPred = model(x)
      // calculate loss
      val loss = lossFunc(yPred.bracketAccess(0), yTrue)
      // calculate pradients through back-propagation
      loss.backward()
      // optimize model parameters
      optimizer.step()
      // ensure gradients are set to zero
      model.zero_grad()
      progressBar.update(n = 1)
    }
    progressBar.close()
  }
  println()

  // In [10]:
  posFiles = glob.glob(baseDir + "test/pos/*.txt")
  negFiles = glob.glob(baseDir + "test/neg/*.txt")
  docTermMatrix = cv.transform(posFiles + negFiles)
  var xTest = docTermMatrix.toarray()
  xTest = torch.tensor(xTest, dtype = torch.float32)
  yPos = np.ones(pyLen(posFiles))
  yNeg = np.zeros(pyLen(negFiles))
  val yTest = np.concatenate(pyList(Seq(yPos, yNeg).toPythonProxy))

  // In [11]:
//  val yPred = xTest `@` w > 0 // How to do this?

  // In [12]:
  def binaryClassificationReport(yTrue: py.Dynamic, yPred: py.Dynamic): Unit = {

  }

  println("Hello, world!")
}
