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
    Vehicle vehicle;
    public Body b2body;
    Vehicle vehicles[] = new Vehicle[5];
    private int positionLastVehicle;
    private int lastVehicleWidth;
    private String typeVehicle;

    public Lane(World world, int coord, int velocity, String direction){

        for(int i=0; i<5; i++){
            if (i == 0){
                positionLastVehicle = 0;
                lastVehicleWidth = 0;
            }
            int randomPosition = positionLastVehicle + lastVehicleWidth + (int)(Math.random() * Gdx.graphics.getWidth());
            positionLastVehicle = randomPosition;

            int randomTypeVehicle = 1 + (int)(Math.random() * 9);
            //gera veículos numa proporção de 3 motos para 3 carros para 2 caminhões
            if (randomTypeVehicle < 4){
                typeVehicle = "moto";
                lastVehicleWidth = 24;
            } else if (randomTypeVehicle < 7){
                typeVehicle = "car";
                lastVehicleWidth = 48;
            } else {
                typeVehicle = "truck";
                lastVehicleWidth = 60;
            }

            Vector2 positionVehicle = new Vector2(randomPosition, coord);
            vehicles[i] = new Vehicle(world, typeVehicle, 3, positionVehicle, direction);
           /* Vector2 positionInitial = new Vector2(Gdx.graphics.getWidth() - (i * 48), coord);
            BodyDef bdef = new BodyDef();
            bdef.position.set(positionInitial);
            bdef.type = BodyDef.BodyType.StaticBody;
            bodies[i] = world.createBody(bdef);

            FixtureDef fdef = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(48,24);

            fdef.shape = shape;
            //fdef.isSensor = true;
            bodies[i].createFixture(fdef);*/
        }
    }

    public void update(){
        for(int i=0;i<5;i++){
            vehicles[i].update(vehicles[i]);
        }
    }

    @Override
    public void dispose() {
        for(int i=0;i<5;i++){
            vehicles[i].dispose();
        }
    }
}
