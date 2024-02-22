package com.example.universitydatabase.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class InstantSerializer extends StdSerializer<Instant> {

  public InstantSerializer() {
    this(null);
  }
  public InstantSerializer(Class<Instant> t) {
    super(t);
  }

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'");
    String instantValue = formatter.format(value.atZone(ZoneOffset.UTC));
    gen.writeString(instantValue);
  }
}
