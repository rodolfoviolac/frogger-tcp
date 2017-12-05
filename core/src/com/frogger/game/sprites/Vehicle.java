package com.frogger.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

public class Vehicle extends Sprite implements Disposable{
    public World world;
    public Body b2body;
    private final int VEHICLE_HEIGHT = 24;
    private int vehicleWitdh;
    private int velocityOfVehicle;
    private int velocityOfVehicleToDirection;
    FixtureDef fdef = new FixtureDef();
    BodyDef bdef = new BodyDef();


    public Vehicle(World world, String type, int velocity, Vector2 position, String direction){
        this.world = world;

        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        if(type.equals("car")){
            vehicleWitdh = 48;
        } else if(type.equals("truck")){
            vehicleWitdh = 72;
        } else vehicleWitdh = 24;

        velocityOfVehicle = velocity;

        if (direction.equals("right")){
            velocityOfVehicleToDirection = velocityOfVehicle;
        } else {
            velocityOfVehicleToDirection = velocityOfVehicle * (-1);
        }


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(vehicleWitdh , VEHICLE_HEIGHT );
        fdef.shape = shape;

        b2body.createFixture(fdef);

        //b2body.applyLinearImpulse(new Vector2(-0.1f, 0),b2body.getWorldCenter(),true);
    }

    public void update(Vehicle vehicle){
        vehicle.b2body.setTransform(vehicle.b2body.getPosition().add(velocityOfVehicleToDirection, 0),0);
    }

    @Override
    public void dispose() {
        world.destroyBody(b2body);
    }
}
