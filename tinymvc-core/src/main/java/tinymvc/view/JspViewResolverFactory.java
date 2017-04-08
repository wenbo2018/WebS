package tinymvc.view;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JspViewResolverFactory extends ViewResolverFactory{

    private static final Log logger = LogFactory.getLog(JspViewResolverFactory.class);

    @Override
    public void init() {
       logger.info("jspViewResolverFactory init ");
    }

    @Override
    public ViewResolver getViewResolverInstance() {
        init();
        return new DefaultJspViewResolver();
    }
}
