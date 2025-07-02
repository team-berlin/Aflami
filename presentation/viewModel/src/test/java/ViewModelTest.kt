import junit.framework.TestCase.assertEquals
import org.junit.Test

class ViewModelTest {

    @Test
    fun testusecase(){
        val myclass= MyViewModel()
        @Test
            assertEquals(5, myclass.add())
    }
}