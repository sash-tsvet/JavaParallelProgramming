public interface Drop<E> {
    public E take();
    public boolean put(E elem);
}
