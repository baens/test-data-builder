package net.baens.testdatabuilder;

import org.junit.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDataBuilderTests {
    @Test
    public void intField_correctOutput(){
        MockData data = TestDataBuilder.create(MockData.class)
            .set(TestDataBuilder.fieldOf(MockData.class).testIntField,2)
            .build();

        assertThat(data.testIntField).isEqualTo(2);
    }

    @Test
    public void stringField_correctOutput(){
        MockData data = TestDataBuilder.create(MockData.class)
            .set(TestDataBuilder.fieldOf(MockData.class).testStringField,"test")
            .build();

        assertThat(data.testStringField).isEqualTo("test");
    }

    @Test
    public void stream_correctSize(){
        Iterable<MockData> data = TestDataBuilder.create(MockData.class)
            .build(5);

        assertThat(data).hasSize(5);
    }

    @Test
    public void intField_testSequences(){
        Iterable<MockData> data = TestDataBuilder.create(MockData.class)
            .set(TestDataBuilder.fieldOf(MockData.class).testIntField,2,4,6)
            .build(3);

        assertThat(data).extracting(d -> d.testIntField)
            .containsSequence(2,4,6);
    }

    @Test
    public void intField_reqpestsSequences(){
        Iterable<MockData> data = TestDataBuilder.create(MockData.class)
            .set(TestDataBuilder.fieldOf(MockData.class).testIntField, 2, 4, 6)
            .build(6);

        assertThat(data).extracting(d -> d.testIntField)
            .containsSequence(2,4,6,2,4,6);
    }

    class MockData {
        public final int testIntField;
        public final String testStringField;
        public final OffsetDateTime testTime;

        public MockData(int testIntField, String testStringField, OffsetDateTime time){
            this.testIntField = testIntField;
            this.testStringField = testStringField;
            this.testTime = time;
        }
    }
}
