package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  @BeforeEach
  public void init(){
    this.primaryTorpedoStore = mock(TorpedoStore.class);
    this.secondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(this.primaryTorpedoStore, this.secondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

    when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);


    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(this.secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

    when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(this.primaryTorpedoStore, times(1)).fire(1);
    verify(this.secondaryTorpedoStore, times(1)).fire(1);

    assertEquals(true, result);
  }

  @Test
  public void fireLasers_Success(){
    // Arrange

    // Act
    boolean result = ship.fireLaser(FiringMode.SINGLE);

    // Assert

    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Fail_Empty(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_Success_Secondary(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

    when(this.primaryTorpedoStore.fire(1)).thenReturn(false);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(this.primaryTorpedoStore, times(0)).fire(1);
    verify(this.secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_ALL_Fail_Empty(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_Success_Primary_Secondary(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(this.secondaryTorpedoStore.isEmpty()).thenReturn(false);

    when(this.primaryTorpedoStore.fire(1)).thenReturn(true);
    when(this.secondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(this.primaryTorpedoStore, times(1)).fire(1);
    verify(this.secondaryTorpedoStore, times(1)).fire(1);
    assertEquals(true, result1);
    assertEquals(true, result2);
  }

  @Test
  public void fireTorpedo_Single_Success_Primary_Unsuccessfull(){
    // Arrange
    when(this.primaryTorpedoStore.isEmpty()).thenReturn(false);

    when(this.primaryTorpedoStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(this.primaryTorpedoStore, times(1)).fire(1);
    assertEquals(false, result);
  }

}
