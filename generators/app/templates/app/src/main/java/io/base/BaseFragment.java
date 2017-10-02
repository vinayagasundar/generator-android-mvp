package <%= appPackage %>.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import <%= appPackage %>.arch.BasePresenter;
import <%= appPackage %>.arch.BaseView;
import <%= appPackage %>.arch.PresenterFactory;
import <%= appPackage %>.arch.PresenterManager;

/**
 * @author vinayagasundar
 *         <p>
 *         Ref : http://blog.bradcampbell.nz/mvp-presenters-that-survive-configuration-changes-part-1/
 */

public abstract class BaseFragment extends Fragment {

    protected BasePresenter presenter;

    private boolean mIsDestroyedBySystem = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = PresenterManager.getInstance()
                .getPresenter(getName(), getPresenterFactory());

        if (presenter != null) {
            presenter.bindView(getBaseView());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mIsDestroyedBySystem = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsDestroyedBySystem = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            if (mIsDestroyedBySystem) {
                presenter.unBindView();
            } else {
                PresenterManager.getInstance().removePresenter(getName());
            }
        }
    }

    public abstract PresenterFactory getPresenterFactory();

    public abstract String getName();

    public abstract BaseView getBaseView();
}
