package cl.nexbyte.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class CarritoSyncRequest {
  @NotEmpty @Valid
  private List<CarritoItemRequest> items;

  public List<CarritoItemRequest> getItems() { return items; }
  public void setItems(List<CarritoItemRequest> items) { this.items = items; }
}
