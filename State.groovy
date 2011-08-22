class State {
	int brains
	int shotguns
	Pool hand
	Pool supply
	Pool used
	
	State() {
		brains = 0
		shotguns = 0
		hand = new Pool()
		supply = new Pool(easy:6, medium:4, hard:3)
		used = new Pool()
	}
	
	State drawDice(Random random) {
//		println "draw"
		State nextState = new State(brains:brains, shotguns:shotguns, hand:hand, supply:supply, used:used)
		
		while(nextState.hand.size < 3) {
			if (supply.size == 0) {
				nextState.supply = nextState.used
				nextState.used = new Pool()
			}
			def type = supply.peek(random)
//			println type
			nextState.supply = nextState.supply.remove(type)
			nextState.hand = nextState.hand.add(type)
		}
		
		return nextState
	}

	static def types = [easy:[brains:3, footsteps:2, shotguns:1],
						medium:[brains:2, footsteps:2, shotguns:2],
						hard:[brains:1, footsteps:2, shotguns:3]]
	
	State roll(Random random) {
//		println "roll"
		State nextState = new State(brains:brains, shotguns:shotguns, hand:hand, supply:supply, used:used)

		hand.each { type ->
			int choice = random.nextInt(6)
			def result = types[type].find { typeResult, typeCount -> choice -= typeCount; choice < 0 }.key
//			println "$type -> $result"
			if (result != "footsteps") {
				nextState[result] = nextState[result] + 1
				nextState.hand.remove(type)
				if (result == "brains") {
					nextState.used.add(type)
				}
			}
			
		}

		return nextState		
	}
	
	boolean getLost() {
		return shotguns >= 3		
	}
	
	int getScore() {
		(lost ? 0 : brains)
	}
}



