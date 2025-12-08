package mcapi.json.recipes;

/* loaded from: classes.dex */
public class MachineValues {
    public boolean reverse;
    public int speed;
    public int temp;
    public int time;
    public int weight;

    public db.model.MachineValues toMachineValues() {
        db.model.MachineValues machineValues = new db.model.MachineValues();
        machineValues.setReverse(this.reverse);
        machineValues.setSpeed(this.speed);
        machineValues.setTemp(this.temp);
        machineValues.setTime(this.time);
        machineValues.setWeight(this.weight);
        return machineValues;
    }
}
