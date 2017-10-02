package <%= appPackage %>.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import <%= appPackage %>.arch.BasePresenter;
import <%= appPackage %>.arch.BaseView;
import <%= appPackage %>.arch.PresenterFactory;
import <%= appPackage %>.arch.PresenterManager;

/**
 * @author vinayagasundar
 *         <p>
 *         Ref : http://blog.bradcampbell.nz/mvp-presenters-that-survive-configuration-changes-part-1/
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BasePresenter presenter;


    private boolean mIsDestroyedBySystem = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterManager.getInstance()
                .getPresenter(getName(), getPresenterFactory());

        if (presenter != null) {
            presenter.bindView(getBaseView());
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mIsDestroyedBySystem = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsDestroyedBySystem = false;
    }


    @Override
    protected void onDestroy() {
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
