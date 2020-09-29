public class SequenceC implements Sequence{

    private static MyThreadLocal<Integer> numberContainer =
            new MyThreadLocal<Integer>(){

        protected Integer initialValue(){
            return 0;
        }
    };

    public int getNumber() {
        numberContainer.set(numberContainer.get()+1);
        return numberContainer.get();
    }

    public static void main(String[] args) {
        SequenceC sequence =new SequenceC();

        ClientThread thread3 =new ClientThread(sequence);
        ClientThread thread1 =new ClientThread(sequence);
        ClientThread thread2 =new ClientThread(sequence);

        thread1.start();
        thread2.start();
        thread3.start();


     }
}
