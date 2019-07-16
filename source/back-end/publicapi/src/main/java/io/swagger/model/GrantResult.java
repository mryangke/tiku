package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

/**
 * GrantResult
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-15T03:10:29.413Z[GMT]")
public class GrantResult {
    @JsonProperty("status")
    private String status = null;

    @JsonProperty("token")
    private UUID token = null;

    public GrantResult status(String status) {
        this.status = status;
        return this;
    }

    /**
     * Get status
     *
     * @return status
     **/
    @ApiModelProperty(value = "")

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GrantResult token(UUID token) {
        this.token = token;
        return this;
    }

    /**
     * Get token
     *
     * @return token
     **/
    @ApiModelProperty(value = "")

    @Valid
    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GrantResult grantResult = (GrantResult) o;
        return Objects.equals(this.status, grantResult.status) &&
                Objects.equals(this.token, grantResult.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, token);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GrantResult {\n");

        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
