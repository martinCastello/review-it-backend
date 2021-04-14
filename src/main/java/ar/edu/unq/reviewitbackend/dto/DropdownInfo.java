package ar.edu.unq.reviewitbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DropdownInfo {

    private String id;
    private String label;

    public DropdownInfo(String id) {
        this.id = id;
        this.label = id;
    }
    
    public DropdownInfo(Long id) {
        this.id = id.toString();
        this.label = this.id;
    }
    
    public DropdownInfo(String id, String label) {
        this.id = id;
        this.label = "[" + id + "] " + label;
    }

    public DropdownInfo(Long id, String label) {
        this.id = id.toString();
        this.label = "[" + id + "] " + label;
    }

    public DropdownInfo(Long id, Long label) {
        this.id = id.toString();
        this.label = "[" + id + "] " + label.toString();
    }

    public DropdownInfo(String id, Long label) {
        this.id = id;
        this.label = "[" + id + "] " + label.toString();
    }
}

