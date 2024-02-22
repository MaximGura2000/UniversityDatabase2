package com.example.universitydatabase.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer extends StdSerializer<ZonedDateTime> {

  public ZonedDateTimeSerializer() {
    this(null);
  }

  public ZonedDateTimeSerializer(Class<ZonedDateTime> timeClass) {
    super(timeClass);
  }

  @Override
  public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value.withZoneSameInstant(ZoneOffset.UTC)));
  }
}
