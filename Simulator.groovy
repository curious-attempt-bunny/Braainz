class Simulator {
	int seed
	Closure actor
	
	int execute() {
		Random random = new Random(seed)
		State state = new State()
		
		while(true) {
			state = state.drawDice(random)
			state = state.roll(random)
			
			if (state.lost) {
//				println "Lost!"
				break
			}
			
			if (actor(state) == 'stop') {
//				println "Stop on $state.score"
				break
			}
		} 
		
		return state.score
	}
}
