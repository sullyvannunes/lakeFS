/*
 * lakeFS API
 * lakeFS HTTP API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.lakefs.clients.api.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.lakefs.clients.api.model.CommitCreation;
import io.lakefs.clients.api.model.ImportLocation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ImportCreation
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class ImportCreation {
  public static final String SERIALIZED_NAME_PATHS = "paths";
  @SerializedName(SERIALIZED_NAME_PATHS)
  private List<ImportLocation> paths = new ArrayList<ImportLocation>();

  public static final String SERIALIZED_NAME_COMMIT = "commit";
  @SerializedName(SERIALIZED_NAME_COMMIT)
  private CommitCreation commit;


  public ImportCreation paths(List<ImportLocation> paths) {
    
    this.paths = paths;
    return this;
  }

  public ImportCreation addPathsItem(ImportLocation pathsItem) {
    this.paths.add(pathsItem);
    return this;
  }

   /**
   * Get paths
   * @return paths
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public List<ImportLocation> getPaths() {
    return paths;
  }


  public void setPaths(List<ImportLocation> paths) {
    this.paths = paths;
  }


  public ImportCreation commit(CommitCreation commit) {
    
    this.commit = commit;
    return this;
  }

   /**
   * Get commit
   * @return commit
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public CommitCreation getCommit() {
    return commit;
  }


  public void setCommit(CommitCreation commit) {
    this.commit = commit;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImportCreation importCreation = (ImportCreation) o;
    return Objects.equals(this.paths, importCreation.paths) &&
        Objects.equals(this.commit, importCreation.commit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paths, commit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ImportCreation {\n");
    sb.append("    paths: ").append(toIndentedString(paths)).append("\n");
    sb.append("    commit: ").append(toIndentedString(commit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

