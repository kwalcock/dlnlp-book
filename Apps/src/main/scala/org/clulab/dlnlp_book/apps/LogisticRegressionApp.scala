package org.clulab.dlnlp_book.apps

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.SeqConverters

class Python {
  val pyLen = py.Dynamic.global.len
  val pyList = py.Dynamic.global.list
  val pyDict = py.Dynamic.global.dict
  val pyFloat = py.Dynamic.global.float
}

object LogisticRegressionApp extends Python with App {
  // In [1]:
  val random = py.module("random")
  val np = py.module("numpy")
  val torch = py.module("torch")
  val tqdm = py.module("tqdm.notebook")

  // set this variable to a number to be used as the random seed
  // or to None if you don't want to set a random seed
  val seedOpt = Some(1234) // None

  seedOpt.foreach { seed =>
    random.seed(seed)
    np.random.seed(seed)
  }

  sigmoid(0f)
  sigmoid(Float.MaxValue)
  sigmoid(Float.MinValue)

  // In [4]:
  val glob = py.module("glob")
  val posFiles = glob.glob("e:/DocumentCollections/aclImdb/train/pos/*.txt")
  val negFiles = glob.glob("e:/DocumentCollections/aclImdb/train/neg/*.txt")

  println(s"number of positive reviews: ${pyLen(posFiles)}")
  println(s"number of negative reviews: ${pyLen(negFiles)}")

  // In [5]:
  val sklearn = py.module("sklearn.feature_extraction.text")
  val cv = sklearn.CountVectorizer(input = "filename")
  val docTermMatrix = cv.fit_transform(posFiles + negFiles)
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
  val yPos = np.ones(pyLen(posFiles))
  val yNeg = np.zeros(pyLen(negFiles))
  val yTrain = np.concatenate(pyList(Seq(yPos, yNeg).toPythonProxy))
  println(yTrain)

  // In [9]:
  // initialize model: the feature vector and bias term are populated with zeros
  val (nExamples, nFeatures) = (xTrain.shape.bracketAccess(0), xTrain.shape.bracketAccess(1))
  var w = np.random.random(nFeatures)

  // In [10]:
  // from scipy.special import expit as sigmoid
  def sigmoid(z: Float): Float = {
    val limit = np.log(np.finfo(pyFloat).max).as[Float]

    if (-z > limit) 0f
    else 1f / (1f + np.exp(-z).as[Float])
  }

  // In [11]:
  val lr = 1e-1f
  val nEpochs = 10
  val indices = np.arange(nExamples)
  Range(0, nEpochs).foreach { epoch =>
    // randomize the order in which training examples are seen in this epoch
    np.random.shuffle(indices)
    // traverse the training data
    val loop = tqdm.tqdm(indices, desc = s"epoch ${epoch + 1}") // Get some kind of range?
    println(loop)
    loop.foreach { i: Int =>
      val x = xTrain.bracketAccess(i)
      val y = yTrain.bracketAccess(i)
      // calculate the derivative of the cost function for this batch
      val derivCost = (sigmoid((x `@` w).as[Float]) - y.as[Float]) * x.as[Float]
      // update the weights
      w = w -lr * derivCost

    }
  }

  println("Hello, world!")
}
