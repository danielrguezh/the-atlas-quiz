package es.ies.puerto.model.entities;

public enum Rank {
    BEGINNER, //0-3000
    TOURIST, //3000-6000
    NOMAD, //6000-9000
    CARTOGRAPHER, //9000-12000
    CARTOGRAPH_MASTER; //+12000

    /**
     * Methot that ranks up
     * @return new rank
     */
    public Rank rankUp() {
        int nextOrdinal = this.ordinal() + 1;
        Rank[] values = Rank.values();
        return nextOrdinal < values.length ? values[nextOrdinal] : this;
    }
}
