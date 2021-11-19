package dungeon.model;

class Otyugh {

  private int health;

  Otyugh() {
    health = 2;
  }

  int getHealth() {
    return health;
  }

  void hit() {
    this.health--;
  }

}
