package com.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.frogger.game.sprites.Vehicle;

public class Lane implements Disposable{
    Vehicle vehicles[];
    private int firstVehicleWidth;
    private int positionFirstVehicle;
    private int positionLastVehicle;
    public int rangeOfVehicles;
    private int lastVehicleWidth;
    private int positionNewVehicle;
    private String typeVehicle;
    private int vehicleWidth;
    private final int NUM_OF_VEHICLES = 5;
    private final int PERCENT_OF_MOTOS = 40;
    private final int PERCENT_OF_CARS = 40;
    private int motoRadius;
    private int carRadius;
    private int truckRadius;

    public Lane(World world, int coord, int velocity, String direction){
        vehicleWidth = 0;
        rangeOfVehicles = 0;
        firstVehicleWidth = 0;
        motoRadius = Vehicle.MOTO_RADIUS;
        carRadius = Vehicle.CAR_RADIUS;
        truckRadius = Vehicle.TRUCK_RADIUS;
        vehicles = new Vehicle[NUM_OF_VEHICLES];
        for(int i=0; i<NUM_OF_VEHICLES; i++){
            vehicleWidth = randomVehicleTypes(vehicleWidth);
            vehiclesPositionsGenerator(i, direction);
            if (i==NUM_OF_VEHICLES-1){
                if (direction.equals("right")){
                    //o cálculo abaixo está correto. Porém rangeOfVehicles não está chegando no update() em Vehicle
                    rangeOfVehicles = (positionFirstVehicle - positionNewVehicle) + (firstVehicleWidth + vehicleWidth);
                    //rangeOfVehicles = 600; <--- foi só um teste
                } else rangeOfVehicles = (positionNewVehicle - positionFirstVehicle) + (firstVehicleWidth + vehicleWidth);
            }
            Vector2 positionVehicle = new Vector2(positionNewVehicle, coord);
            vehicles[i] = new Vehicle(world, typeVehicle, velocity, positionVehicle, direction, rangeOfVehicles);
        }
    }


    private int randomVehicleTypes(int vehicleWidth){
        lastVehicleWidth = vehicleWidth;
        int randomTypeVehicle = 1 + (int)(Math.random() * 101);
        //gera veículos numa proporção de 5 motos para 4 carros para 1 caminhões
        if (randomTypeVehicle < PERCENT_OF_MOTOS){
            typeVehicle = "moto";
            vehicleWidth = motoRadius;
        } else if (randomTypeVehicle < PERCENT_OF_MOTOS + PERCENT_OF_CARS){
            typeVehicle = "car";
            vehicleWidth = carRadius;
        } else {
            typeVehicle = "truck";
            vehicleWidth = truckRadius;
        }
        lastVehicleWidth = lastVehicleWidth + vehicleWidth;
        return vehicleWidth;
    }

    private void vehiclesPositionsGenerator(int i, String direction){
        int randomPosition = 1 + (int)(Math.random() * (Gdx.graphics.getWidth()*2 - Gdx.graphics.getWidth()/2));
        if (i == 0){ //o primeiro veículo aparece em alguma posicao dentro da tela
            positionNewVehicle = randomPosition;
            positionLastVehicle = positionNewVehicle;
            positionFirstVehicle = positionNewVehicle;
            firstVehicleWidth = vehicleWidth;
        } else if(direction.equals("right")){
            positionNewVehicle = positionLastVehicle - lastVehicleWidth - randomPosition;
            positionLastVehicle = positionNewVehicle;
        } else if(direction.equals("left")){
            positionNewVehicle = positionLastVehicle + lastVehicleWidth + randomPosition;
            positionLastVehicle = positionNewVehicle;
        }
    }

    public void update(){
        for(int i=0;i<NUM_OF_VEHICLES;i++){
            vehicles[i].update();
        }
    }

    @Override
    public void dispose() {
        for(int i=0;i<NUM_OF_VEHICLES;i++){
            vehicles[i].dispose();
        }
    }
}
