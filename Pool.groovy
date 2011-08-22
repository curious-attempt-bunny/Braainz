class Pool {
	int easy
	int medium
	int hard
	
	int getSize() {
		easy + medium + hard
	}
	
	def peek(Random random) {
		int choice = random.nextInt(size)
		def type = ["easy", "medium", "hard"].find { poolType -> choice -= this[poolType] ; choice < 0 }
		return type
	}

	def remove(String type) {
		def nextPool = new Pool(easy:easy, medium:medium, hard:hard)
		nextPool[type] = nextPool[type] - 1
		return nextPool
	}
	
	def add(String type) {
		def nextPool = new Pool(easy:easy, medium:medium, hard:hard)
		nextPool[type] = nextPool[type] + 1
		return nextPool
	}
	
	def each(Closure closure) {
		["easy", "medium", "hard"].each { type ->
			this[type].times {
				closure(type)
			}
		}
		return this
	}
}