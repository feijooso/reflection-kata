import com.kata.Serializer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SerializerTest {
    @Test
    fun `serialize int`() {
        assertThat(serializer.serialize(3)).isEqualTo("3")
    }

    @Test
    fun `serialize float`() {
        assertThat(serializer.serialize(3.5F)).isEqualTo("3.5")
    }

    @Test
    fun `serialize string`() {
        assertThat(serializer.serialize("hola")).isEqualTo("\"hola\"")
    }

    @Test
    fun `serialize simple object`() {
        val simpleObject = SimpleObject("value", 3, 3.5F)

        assertThat(serializer.serialize(simpleObject)).isEqualTo("{\"float\":3.5,\"integer\":3,\"string\":\"value\"}")
    }

    @Test
    fun `serialize compound object`() {
        val compoundObject = CompoundObject("pepe", SimpleObject("hola", 2, 3.5F))

        assertThat(serializer.serialize(compoundObject)).isEqualTo("{\"atrib1\":\"pepe\",\"atrib2\":{\"float\":3.5,\"integer\":2,\"string\":\"hola\"}}")
    }

    @Test
    fun `deserialize string`() {
        assertThat(serializer.deserialize("\"hola\"", String::class)).isEqualTo("hola")
    }

    @Test
    fun `deserialize int`() {
        assertThat(serializer.deserialize("1", Int::class)).isEqualTo(1)
    }

    @Test
    fun `deserialize float`() {
        assertThat(serializer.deserialize("1.5", Float::class)).isEqualTo(1.5F)
    }

    @Test
    fun `deserialize simple object`() {
        assertThat(serializer.deserialize("{\"float\":3.5,\"integer\":3,\"string\":\"value\"}", SimpleObject::class)).isEqualTo(SimpleObject("value", 3, 3.5F))

    }

    private var serializer = Serializer()
}
