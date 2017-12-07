package com.frogger.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

public class Vehicle extends Sprite implements Disposable{
    private World world;
    private Body b2body;

    private FixtureDef fdef = new FixtureDef();
    private BodyDef bdef = new BodyDef();

    private final int VEHICLE_HEIGHT = 24;
    public static final int MOTO_RADIUS = 24;
    public static final int CAR_RADIUS = 48;
    public static final int TRUCK_RADIUS = 72;
    private int vehicleWitdh;
    private int velocityOfVehicle;
    private int velocityOfVehicleToDirection;
    public int rangeOfVehicles;
    private String vehicleDirection;


    public Vehicle(World world, String type, int velocity, Vector2 position, String direction, int rangeOfVehicles){
        this.world = world;
        this.rangeOfVehicles = rangeOfVehicles;
        vehicleDirection = direction;
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        setVehicleWidth(type);
        setVehicleMovement(velocity, direction);
        createVehicleBox();
    }

    private void setVehicleWidth(String type){
        if(type.equals("car")){
            vehicleWitdh = CAR_RADIUS;
        } else if(type.equals("truck")){
            vehicleWitdh = TRUCK_RADIUS;
        } else vehicleWitdh = MOTO_RADIUS;
    }

    private void setVehicleMovement(int velocity, String direction){
        velocityOfVehicle = velocity;
        if (direction.equals("right")){
            velocityOfVehicleToDirection = velocityOfVehicle;
        } else {
            velocityOfVehicleToDirection = velocityOfVehicle * (-1);
        }
    }

    private void createVehicleBox(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(vehicleWitdh , VEHICLE_HEIGHT );
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void update(){
        if (vehicleDirection.equals("right")){
            if (b2body.getPosition().x == Gdx.graphics.getWidth() + vehicleWitdh){
                //rangeOfVehicles tá chegando sempre zero, pq???
                this.b2body.setTransform(new Vector2(this.b2body.getPosition().x - (rangeOfVehicles + vehicleWitdh + 120), b2body.getPosition().y), 0);
                Gdx.app.log("dir", "");
            }
        } else if (b2body.getPosition().x == 2*(-1)*vehicleWitdh){
            Gdx.app.log("esq", "");
            this.b2body.setTransform(new Vector2(this.b2body.getPosition().x + rangeOfVehicles + vehicleWitdh + 120, b2body.getPosition().y), 0);
        }
        this.b2body.setTransform(this.b2body.getPosition().add(velocityOfVehicleToDirection, 0),0);
    }

    @Override
    public void dispose() {
        world.destroyBody(b2body);
    }
}
