module asvc {
    requires javafx.controls;
    requires javafx.graphics;
    requires org.apache.logging.log4j;

    opens com.dbarenholz.asvc.vocabitem to javafx.base;
    exports com.dbarenholz.asvc;
}


