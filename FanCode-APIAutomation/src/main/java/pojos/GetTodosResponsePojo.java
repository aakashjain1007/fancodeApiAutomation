package pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetTodosResponsePojo {
    private int userId;
    private int id;
    private String title;
    private Boolean completed;
}
