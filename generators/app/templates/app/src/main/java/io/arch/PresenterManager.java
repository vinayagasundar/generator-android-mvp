package <%= appPackage %>.arch;

import android.support.v4.util.SimpleArrayMap;

/**
 * @author vinayagasundar
 */

public class PresenterManager {

    private static PresenterManager mInstance;

    private SimpleArrayMap<String, BasePresenter> mPresenterCache = new SimpleArrayMap<>();

    private PresenterManager() {

    }

    public static PresenterManager getInstance() {
        if (mInstance == null) {
            mInstance = new PresenterManager();
        }

        return mInstance;
    }

    /**
     * Get the Presenter for given name
     * @param who Name of the Presenter which we need to created
     * @param presenterFactory Presenter Factory to create a new Presenter
     * @return instance of the Presenter
     */
    public BasePresenter getPresenter(String who, PresenterFactory presenterFactory) {
        BasePresenter presenter = null;


        presenter = mPresenterCache.get(who);

        if (presenter == null) {
            presenter = presenterFactory.create();
            mPresenterCache.put(who, presenter);
        }

        return presenter;
    }


    /**
     * Remove the Presenter from the Cache..
     * @param who Name of the Presenter which need to remove
     */
    public void removePresenter(String who) {
        mPresenterCache.remove(who);
    }


}
