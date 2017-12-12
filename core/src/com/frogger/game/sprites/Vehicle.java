package com.frogger.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.frogger.game.Lane;

public class Vehicle extends Sprite{
    private Body b2body;

    private Texture vehicleTexture;

    public static final int MOTO_RADIUS = 24;
    public static final int CAR_RADIUS = 48;
    public static final int TRUCK_RADIUS = 72;
    private final int VEHICLE_HEIGHT = 24;
    private final int DISTANCE_TO_LAST_VEHICLE = 244;
    private int vehicleWitdh;
    private int velocityOfVehicleToDirection;
    private String vehicleDirection;
    private boolean vehicleSpecial;
    private float coordYInitial;
    private Lane lane;


    public Vehicle(World world, String type, int velocity, Vector2 position, String direction, boolean vehicleSpecial, Lane lane){
        FixtureDef fdef = new FixtureDef();
        BodyDef bdef = new BodyDef();
        this.lane = lane;
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        this.vehicleSpecial = vehicleSpecial;
        coordYInitial = position.y;
        vehicleDirection = direction;

        setVehicleWidth(type);
        setVehicleMovement(velocity, direction);
        createVehicleBox(fdef);
        setVehicleTexture(type, direction);
    }

    private void setVehicleWidth(String type){
        if(type.equals("car")){
            vehicleWitdh = CAR_RADIUS;
        } else if(type.equals("truck")){
            vehicleWitdh = TRUCK_RADIUS;
        } else vehicleWitdh = MOTO_RADIUS;
    }

    private void setVehicleMovement(int velocity, String direction){
        if (direction.equals("right")){
            velocityOfVehicleToDirection = velocity;
        } else {
            velocityOfVehicleToDirection = velocity * (-1);
        }
    }

    private void createVehicleBox(FixtureDef fdef){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(vehicleWitdh, VEHICLE_HEIGHT);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    private void setVehicleTexture(String type, String direction){
        if (type.equals("moto")){
            if (direction.equals("right")){
                vehicleTexture = new Texture("vehicles/moto_right.png");
            } else {
                vehicleTexture = new Texture("vehicles/moto_left.png");
            }
        } else if (type.equals("car")){
            if (direction.equals("right")){
                vehicleTexture = new Texture("vehicles/car_right.png");
            } else {
                vehicleTexture = new Texture("vehicles/car_left.png");
            }
        } else if (direction.equals("right")){
            vehicleTexture = new Texture("vehicles/truck_right.png");
        } else {
            vehicleTexture = new Texture("vehicles/truck_left.png");
        }
    }

    public void drawVehicles(SpriteBatch sb){
        sb.draw(vehicleTexture, b2body.getPosition().x - vehicleWitdh, b2body.getPosition().y - VEHICLE_HEIGHT);
    }

    public int getPositionX(){
        return (int) b2body.getPosition().x;
    }

    public void update(int rangeOfVehicles, int distanceOfReposition){
        if (vehicleDirection.equals("right")){
            if (b2body.getPosition().x >= Gdx.graphics.getWidth() + distanceOfReposition){
                b2body.setTransform(b2body.getPosition().x, coordYInitial, 0);
                b2body.setTransform(new Vector2(Gdx.graphics.getWidth() - rangeOfVehicles - DISTANCE_TO_LAST_VEHICLE, b2body.getPosition().y), 0);
            }
            if (b2body.getPosition().x == Gdx.graphics.getWidth() / 2){
                float newCoordY = lane.coordNewLane(b2body.getPosition().y);
                b2body.setTransform(new Vector2(b2body.getPosition().x, newCoordY), 0);
            }
        } else if (b2body.getPosition().x <= -distanceOfReposition){
            b2body.setTransform(b2body.getPosition().x, coordYInitial, 0);
            b2body.setTransform(new Vector2(rangeOfVehicles + DISTANCE_TO_LAST_VEHICLE, b2body.getPosition().y), 0);
            if (b2body.getPosition().x == Gdx.graphics.getWidth() / 2 && vehicleSpecial){
                float newCoordY = lane.coordNewLane(b2body.getPosition().y);
                b2body.setTransform(new Vector2(b2body.getPosition().x, newCoordY), 0);
            }
        }
        b2body.setTransform(this.b2body.getPosition().add(velocityOfVehicleToDirection, 0),0);
    }

    public void dispose(World world) {
        world.destroyBody(b2body);
    }
}
