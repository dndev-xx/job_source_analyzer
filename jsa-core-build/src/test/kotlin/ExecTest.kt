import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import org.example.exec.fetchDataFromSourceHH
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ExecTest {

    @Test
    fun testFetchDataFromSourceHH_SuccessfulResponse() {
        val mockClient = mock<OkHttpClient>()
        val mockCall = mock<Call>()
        val mockResponse = mock<Response>()
        val mockResponseBody = mock<ResponseBody>()

        `when`(mockClient.newCall(any())).thenReturn(mockCall)
        `when`(mockCall.execute()).thenReturn(mockResponse)
        `when`(mockResponse.isSuccessful).thenReturn(true)
        `when`(mockResponse.body).thenReturn(mockResponseBody)
        `when`(mockResponseBody.string()).thenReturn("Your JSON response here")

        val source = "https://api.hh.ru/vacancies?text=java&page=1&per_page=2"
        val result = fetchDataFromSourceHH(source)
        assertNotNull(result)
        assertEquals(2, result.items.size)
    }

    @Test
    fun testFetchDataFromSourceHH_FailedResponse() {
        val mockClient = mock<OkHttpClient>()
        val mockCall = mock<Call>()
        val mockResponse = mock<Response>()
        doReturn(mockCall).`when`(mockClient).newCall(any())
        doReturn(mockResponse).`when`(mockCall).execute()
        doReturn(false).`when`(mockResponse).isSuccessful
        doReturn(404).`when`(mockResponse).code
        val result = fetchDataFromSourceHH("https://example.com")
        assertNull(result)
    }
}