package rivon0507.centremedical;

import rivon0507.centremedical.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.boot();
        HibernateUtil.shutdown();
    }
}