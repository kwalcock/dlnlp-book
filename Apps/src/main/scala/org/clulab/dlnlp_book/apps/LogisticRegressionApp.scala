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
  // In [1]:
  val random = py.module("random")
  val np = py.module("numpy") // .as[NumPy]
  val torch = py.module("torch")
  val tqdm = py.module("tqdm") // .notebook")

  implicit val sth: ObjectReader[NDArray[Float]] = NDArray.seqReader[Float]

  val indices1 = np.arange(10)
  val loop = tqdm.tqdm(indices1, desc = s"epoch hello") // Get some kind of range?
  Range(0, 10).foreach { index =>
    Thread.sleep(200)
    loop.update(n = 1)
  }
  println()

   // set this variable to a number to be used as the random seed
  // or to None if you don't want to set a random seed
  val seedOpt = Some(1234) // None

  seedOpt.foreach { seed =>
    random.seed(seed)
    np.random.seed(seed)
  }

  // In [4]:
  val glob = py.module("glob")
  var posFiles = glob.glob("e:/DocumentCollections/aclImdb/train/pos/*.txt")
  var negFiles = glob.glob("e:/DocumentCollections/aclImdb/train/neg/*.txt")

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
  // Append 1s to the xs; this will allow us to multiply by the weights and
  // the bias in a single pass.
  // Make an array with a one for each row/data point
  val ones = np.ones(xTrain.shape.bracketAccess(0))
  // Concatenate these ones to existing feature vectors
  xTrain = np.column_stack((xTrain, ones))
  println(xTrain.shape)

  // In [8]:
  // training labels
  var yPos = np.ones(pyLen(posFiles))
  var yNeg = np.zeros(pyLen(negFiles))
  val yTrain = np.concatenate(pyList(Seq(yPos, yNeg).toPythonProxy))
  println(yTrain)

  // In [9]:
  // initialize model: the feature vector and bias term are populated with zeros
  val (nExamples, nFeatures) = (xTrain.shape.bracketAccess(0), xTrain.shape.bracketAccess(1))
  var w = np.random.random(nFeatures)

  CPythonInterpreter.execManyLines(
    """|
       |""".stripMargin
  )

  // In [10]:
  // from scipy.special import expit as sigmoid
  // See sigmoid above.
  val sigmoid: (NDArray[Float]) => py.Dynamic = {
    val limit = np.log(np.finfo(pyFloat).max)

    (z: NDArray[Float]) => {
      val negZ = -z // np.negative(z) // .as[Float]

//      if (negZ.`>`(limit)) NDArray.zeros(1)
//      else
        np.ones(1) / (np.ones(1) + np.exp(negZ))
    }
  }

  // In [11]:
  val lr = 1e-1f
  val nEpochs = 10

  val model = torch.nn.Linear(nFeatures, 1)
  val lossFunc = torch.nn.BCEWithLogitLoss()
  val optimizer = torch.optim.SGD(model.parameters(), lr = lr)


  val indices = np.arange(nExamples)
  Range(0, nEpochs).foreach { epoch =>
    // randomize the order in which training examples are seen in this epoch
    np.random.shuffle(indices)
    // traverse the training data
    val loop = tqdm.tqdm(indices, desc = s"epoch ${epoch + 1}") // Get some kind of range?
    Range(0, nExamples.as[Int]).foreach { i =>
      loop.update(n = 1)
      val x = xTrain.bracketAccess(i)
      val y = yTrain.bracketAccess(i)
      // calculate the derivative of the cost function for this batch
      val step1 = x.dot(w)
      val step2 = step1.as[NDArray[Float]]
      val step3 = sigmoid(step2)
      val step4 = step3 - y
      val step5 = step4 * x
      val derivCost = step5
      // update the weights
      w = w -lr * derivCost
    }
  }
  println()

  // In [12]:
  posFiles = glob.glob("e:/DocumentCollections/aclImdb/test/pos/*.txt")
  negFiles = glob.glob("e:/DocumentCollections/aclImdb/test/neg/*.txt")
  docTermMatrix = cv.transform(posFiles + negFiles)
  var xTest = docTermMatrix.toarray()
  xTest = np.column_stack((xTest, np.ones(xTest.shape.bracketAccess(0))))
  yPos = np.ones(pyLen(posFiles))
  yNeg = np.zeros(pyLen(negFiles))
  val yTest = np.concatenate(pyList(Seq(yPos, yNeg).toPythonProxy))

  // In [13]:
  val yPred = xTest `@` w > 0

  def binaryClassificationReport(yTrue: Dynamic, yPred: Dynamic): Unit = {

  }

  println("Hello, world!")
}
