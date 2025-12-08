package machineAdapter;

/* loaded from: classes.dex */
public interface IMachineAdapter extends IJogDialHandler {
    ICommandInterface getCommandInterface();

    void registerMachineCallback(IMachineCallback iMachineCallback);

    void shutDown();

    void unregisterMachineCallback(IMachineCallback iMachineCallback);
}
