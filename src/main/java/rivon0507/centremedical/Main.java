package rivon0507.centremedical;

import lombok.extern.slf4j.Slf4j;
import rivon0507.centremedical.util.HibernateUtil;

@Slf4j
public class Main {
    public static void main(String[] args) {
        HibernateUtil.boot();
        // Here goes the code
        HibernateUtil.shutdown();
    }
}