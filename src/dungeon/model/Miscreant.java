package dungeon.model;

class Miscreant {

  private final int[] currentPosition;

  Miscreant(int row, int col) {
    this.currentPosition = new int[]{row, col};
  }

  /**
   * Get the current position of the monster in the dungeon.
   *
   * @return [row, col]
   */
  int[] getCurrentPosition() {
    return currentPosition;
  }

  /**
   * Update the current position of the monster.
   *
   * @param row the new roe
   * @param col the new col
   */
  void setCurrentPosition(int row, int col) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Row and col cannot be negative!");
    }
    this.currentPosition[0] = row;
    this.currentPosition[1] = col;
  }
}