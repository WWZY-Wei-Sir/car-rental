package wsir.carrental.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    private Integer photoId;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private LocalDateTime uploadTime;
}
