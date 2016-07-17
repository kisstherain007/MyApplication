package commponents;

/**
 * Created by archermind on 16-1-19.
 */
public interface PagerNavCallback {
    void notfiyInit(int size);

    void notifyPagerChanged(int position);
}
