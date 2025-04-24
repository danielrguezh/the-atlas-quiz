package es.ies.puerto.model.entities;

public enum Rank {
    BEGINNER,
    TOURIST, 
    NOMAD,
    CARTOGRAPHER, 
    CARTOGRAPH_MASTER;

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
