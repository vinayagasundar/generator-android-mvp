package  <%= appPackage %>.arch;

/**
 * @author vinayagasundar
 */

public interface BasePresenter {

    void bindView(BaseView view);

    void unBindView();
}
