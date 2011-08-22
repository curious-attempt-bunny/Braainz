def actor = { state ->
	if (state.shotguns == 2 && state.score > 0) "stop"
}

Random seedGenerator = new Random(1234) // repeatable
int sampleSize = 1000

int totalScore = (0..sampleSize).sum {
	return new Simulator(seed:seedGenerator.nextInt(), actor:actor).execute()
}

println "Benchmark: average of ${totalScore/sampleSize}"