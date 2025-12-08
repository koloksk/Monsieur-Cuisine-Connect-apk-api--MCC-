package mcapi.json.recipes;

/* loaded from: classes.dex */
public class Measurement {
    public boolean lid;
    public int speed;
    public int temp;
    public int weight;

    public db.model.Measurement toMeasurement() {
        db.model.Measurement measurement = new db.model.Measurement();
        measurement.setLid(this.lid);
        measurement.setSpeed(this.speed);
        measurement.setTemp(this.temp);
        measurement.setWeight(this.weight);
        return measurement;
    }
}
