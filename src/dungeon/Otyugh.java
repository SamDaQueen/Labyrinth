package dungeon;

class Otyugh {

  private Health health;

  Otyugh() {
    health = Health.FULL;
  }

  Health getHealth() {
    return health;
  }

  void setHealth(Health health) {
    this.health = health;
  }


}
