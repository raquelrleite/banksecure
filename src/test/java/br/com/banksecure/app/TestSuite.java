package br.com.banksecure.app;

import br.com.banksecure.app.service.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ApoliceServiceTest.class,
        BemServiceTest.class,
        ClienteServiceTest.class,
        FuncionarioServiceTest.class,
        SeguroServiceTest.class
})
public class TestSuite {


}
