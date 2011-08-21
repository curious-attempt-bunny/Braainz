def state = [brains:0, shotguns:0, table:[easy:0, medium:0, hard:0], pool:[easy:6, medium:4, hard:3], usedPool:[easy:0, medium:0, hard:0]]
def types = [easy:[brains:3, footsteps:2, shotguns:1], medium:[brains:2, footsteps:2, shotguns:2], hard:[brains:1, footsteps:2, shotguns:3]]
def actor(state) {
  if (state.shotguns == 2) "stop"
}

def random = new Random(1234)

while(true) {
  def action = actor(state)
  if (action == "stop") {
    break;
  }
  while(state.table.values().sum() < 3) {
    def poolSize = state.pool.values().sum()
    if (poolSize == 0) {
      state.pool = state.usedPool
      state.usedPool = [easy:0, medium:0, hard:0]
      poolSize = state.pool.values().sum()
    }
    int choice = random.nextInt(poolSize) 
    def type = state.pool.find { poolType, poolCount -> choice -= poolCount ; choice < 0 }.key
    state.pool[type] = state.pool[type] - 1
    state.table[type]++
  }
  println "playing: $state.table"
  state.table.each { type, count ->
    count.times {
      int choice = random.nextInt(6)
      def result = types[type].find { typeResult, typeCount -> choice -= typeCount; choice < 0 }.key
      println "$type -> $result"
      if (result != "footsteps") {
        state[result] = state[result] + 1
        state.table[type] = state.table[type] - 1
        if (result == "brains") {
          state.usedPool[type] = state.usedPool[type] + 1
        }
      }
    }
  }
  println "leaving: $state.table"
  println "brains: $state.brains, shotguns: $state.shotguns"
  if (state.shotguns >= 3) break;
}

println "State: $state"


