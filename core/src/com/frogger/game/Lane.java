package com.frogger.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.frogger.game.sprites.Vehicle;

import java.util.Random;

public class Lane{
    private Vehicle vehicles[];
    private String direction;
    private int numOfLane;
    private int positionFirstVehicle;
    private int positionLastVehicle;
    private int lastVehicleWidth;
    private int positionNewVehicle;
    private int rangeOfVehicles;
    private String typeVehicle;
    private final int NUM_OF_VEHICLES = 5;
    private final int PERCENT_OF_MOTOS = 40;
    private final int PERCENT_OF_CARS = 40;
    private final int DISTANCE_OF_REPOSITION = 200;
    private final int LANE_RADIUS = 24;
    private StageGame stageGame;

    public Lane(World world, int distanceOfLanes, int velocity, String direction, StageGame stageGame, int numOfLane){
        this.numOfLane = numOfLane;
        this.direction = direction;
        this.stageGame = stageGame;
        boolean vehicleSpecial;
        vehicles = new Vehicle[NUM_OF_VEHICLES];

        int vehicleWidth = 0;
        for(int i=0; i<NUM_OF_VEHICLES; i++){
            vehicleWidth = randomVehicleTypes(vehicleWidth, Vehicle.MOTO_RADIUS, Vehicle.CAR_RADIUS, Vehicle.TRUCK_RADIUS);
            vehiclesPositionsGenerator(i, direction);
            if (i == NUM_OF_VEHICLES - 1){
                if (direction.equals("right")){
                    rangeOfVehicles = (positionFirstVehicle - positionNewVehicle);
                } else {
                    rangeOfVehicles = (positionNewVehicle - positionFirstVehicle);
                }
            }
            int vehicleIsSpecial = 1 + (int)(Math.random() * 2);
            if (vehicleIsSpecial==1){
                vehicleSpecial = true;
            } else vehicleSpecial = false;

            int coord = distanceOfLanes * numOfLane + LANE_RADIUS;

            Vector2 positionVehicle = new Vector2(positionNewVehicle, coord);
            vehicles[i] = new Vehicle(world, typeVehicle, velocity, positionVehicle, direction, vehicleSpecial, this);

        }
    }

    public float coordNewLane(float coordYVehicle){
        int deltaCoordY = stageGame.changeLane(numOfLane);
        return deltaCoordY + coordYVehicle;
    }

    public boolean haveSpaceToChange(){
        int screenCenter = Gdx.graphics.getWidth() / 2;
        boolean haveVehicle = false;
        for (int i=0; i<NUM_OF_VEHICLES; i++){
            if (vehicles[i].getPositionX() > screenCenter - 150 && vehicles[i].getPositionX() <= screenCenter + 150) {
                haveVehicle = true;
            }
        }
        if (haveVehicle) {
            return false;
        } else return true;
    }

    public String getDirection(){
        return direction;
    }

    private int randomVehicleTypes(int vehicleWidth, int motoRadius, int carRadius, int truckRadius){
        lastVehicleWidth = vehicleWidth;
        int randomTypeVehicle = 1 + (int)(Math.random() * 101);
        //gera veículos numa frequencia de 40% motos, 40% carros e 20% caminhoes
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
        int randomPosition = 1 + (int)(Math.random() * 500);
        if (i == 0){ //o primeiro veículo aparece em alguma posicao dentro da tela
            positionNewVehicle = randomPosition;
            positionLastVehicle = positionNewVehicle;
            positionFirstVehicle = positionNewVehicle;
        } else if(direction.equals("right")){
            positionNewVehicle = positionLastVehicle - lastVehicleWidth - randomPosition;
            positionLastVehicle = positionNewVehicle;
        } else if(direction.equals("left")){
            positionNewVehicle = positionLastVehicle + lastVehicleWidth + randomPosition;
            positionLastVehicle = positionNewVehicle;
        }
    }

    public void draw(SpriteBatch sb){
        for (int i=0; i<NUM_OF_VEHICLES; i++){
            vehicles[i].drawVehicles(sb);
        }
    }

    public void update(){
        for(int i=0;i<NUM_OF_VEHICLES;i++){
            vehicles[i].update(rangeOfVehicles, DISTANCE_OF_REPOSITION);
        }
    }

    public void dispose(World world) {
        for(int i=0;i<NUM_OF_VEHICLES;i++){
            vehicles[i].dispose(world);
        }
    }
}
