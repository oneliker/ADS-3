import java.util.Random;

public class MyHashTable<K, V> {
    private static final int DEFAULT_SIZE = 101;
    private HashNode<K, V>[] table;
    private int size;

    public MyHashTable() {
        this(DEFAULT_SIZE);
    }

    public MyHashTable(int size) {
        this.table = new HashNode[size];
        this.size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> node = table[index];

        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.next;
        }

        table[index] = new HashNode<>(key, value, table[index]);
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> node = table[index];

        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }

        return null;
    }

    public void printBucketSizes() {
        for (int i = 0; i < table.length; i++) {
            HashNode<K, V> node = table[i];
            int count = 0;

            while (node != null) {
                count++;
                node = node.next;
            }

            System.out.println("Box " + i + " has " + count + " elements.");
        }
    }

    private static class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value, HashNode<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public static class MyTestingClass {
        private int id;

        public MyTestingClass(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof MyTestingClass) {
                MyTestingClass other = (MyTestingClass) obj;
                return this.id == other.id;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        MyHashTable<MyTestingClass, String> hashTable = new MyHashTable<>();
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            int randomId = random.nextInt(100000);
            hashTable.put(new MyTestingClass(randomId), "Value " + i);
        }

        hashTable.printBucketSizes();
    }
}