package com.sandman.form;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by sandman on 17. 4. 19.
 */
@Data
public class PostForm {
    @NotNull
    @Size(min = 1, max = 125)
    private String title;

    @NotNull
    @Size(min = 1)
    private String postBody;
}
