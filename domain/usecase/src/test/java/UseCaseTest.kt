import com.berlin.usecase.MyClass
import junit.framework.TestCase.assertEquals
import org.junit.Test


class UseCaseTest {

        val myclass= MyClass()
    @Test
    fun testusecase(){
        assertEquals(5, myclass.add())
    }
}