package EngineTester;

import Entity.Entity;

import GUI.GuiRenderer;
import GUI.GuiTexture;
import LibrarySupport.Vector2f;
import LibrarySupport.Vector3f;
import Models.RawModel;
import Models.TexturedModel;
import OBJFileLoader.ModelData;
import OBJFileLoader.OBJFileLoader;
import RenderEngine.*;
import Shaders.StaticShader;
import Terrain.Terrain;
import Textures.ModelTexture;
import Entity.Camera;
import Entity.Light;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;
import Entity.Player;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

    public static WindowManager window;
    public static void main(String[] args) {

        window = new WindowManager();
        window.init();

        Loader loader = new Loader();

//      MAP
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("road"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("map"));
        Terrain terrain = new Terrain(0,-1,loader, texturePack, blendMap, "heightmap");

//        ========================================================================================

//        OBJ

        ModelData data = OBJFileLoader.loadOBJ("lowPolyTree");
        RawModel treeModel = loader.loadToVAO(data.getVertices(),data.getTextureCoords(), data.getNormals(), data.getIndices());
        ModelTexture treeText = new ModelTexture(loader.loadTexture("lowPolyTreess"));
        treeText.setNumberOfRows(2);
        TexturedModel staticModel = new TexturedModel(treeModel, treeText);


        ModelData treeM = OBJFileLoader.loadOBJ("tree");
        RawModel tanenbaum = loader.loadToVAO(treeM.getVertices(),treeM.getTextureCoords(), treeM.getNormals(), treeM.getIndices());
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("tree"));
        TexturedModel tree = new TexturedModel(tanenbaum,treeTexture);

        ModelData h1 = OBJFileLoader.loadOBJ("house1");
        RawModel house1Model = loader.loadToVAO(h1.getVertices(), h1.getTextureCoords(), h1.getNormals(), h1.getIndices());
        ModelTexture house1Tex = new ModelTexture(loader.loadTexture("house1"));
        TexturedModel house1 = new TexturedModel(house1Model,house1Tex);

        ModelData h2 = OBJFileLoader.loadOBJ("house2");
        RawModel house2Model = loader.loadToVAO(h2.getVertices(), h2.getTextureCoords(), h2.getNormals(), h2.getIndices());
        ModelTexture house2Tex = new ModelTexture(loader.loadTexture("house2"));
        TexturedModel house2 = new TexturedModel(house2Model,house2Tex);

        ModelData h3 = OBJFileLoader.loadOBJ("house3");
        RawModel house3Model = loader.loadToVAO(h3.getVertices(), h3.getTextureCoords(), h3.getNormals(), h3.getIndices());
        ModelTexture house3Tex = new ModelTexture(loader.loadTexture("house3"));
        TexturedModel house3 = new TexturedModel(house3Model,house3Tex);

        ModelData h4 = OBJFileLoader.loadOBJ("house4");
        RawModel house4Model = loader.loadToVAO(h4.getVertices(), h4.getTextureCoords(), h4.getNormals(), h4.getIndices());
        ModelTexture house4Tex = new ModelTexture(loader.loadTexture("house4"));
        TexturedModel house4 = new TexturedModel(house4Model,house4Tex);

        ModelData h5 = OBJFileLoader.loadOBJ("house5");
        RawModel house5Model = loader.loadToVAO(h5.getVertices(), h5.getTextureCoords(), h5.getNormals(), h5.getIndices());
        ModelTexture house5Tex = new ModelTexture(loader.loadTexture("house5"));
        TexturedModel house5 = new TexturedModel(house5Model,house5Tex);

        ModelData person = OBJFileLoader.loadOBJ("person");
        RawModel personModel = loader.loadToVAO(person.getVertices(), person.getTextureCoords(), person.getNormals(), person.getIndices());
        ModelTexture personText = new ModelTexture(loader.loadTexture("playerTexture"));
        TexturedModel starter = new TexturedModel(personModel,personText);
        ModelTexture personD = new ModelTexture(loader.loadTexture("playerDead"));
        TexturedModel dead = new TexturedModel(personModel, personD);



//      GUI
        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("GUI"), new Vector2f(-0.65f,0.65f),
                new Vector2f(0.35f,0.35f));
        GuiTexture healthFull = new GuiTexture(loader.loadTexture("healthFull"), new Vector2f(-0.8f,-0.9f), new Vector2f(0.2f,0.2f));
        GuiTexture health5 = new GuiTexture(loader.loadTexture("health4"), new Vector2f(-0.8f,-0.9f), new Vector2f(0.2f,0.2f));
        GuiTexture health3 = new GuiTexture(loader.loadTexture("health2"), new Vector2f(-0.8f,-0.9f), new Vector2f(0.2f,0.2f));
        GuiTexture health1 = new GuiTexture(loader.loadTexture("health1"), new Vector2f(-0.8f,-0.9f), new Vector2f(0.2f,0.2f));
        guis.add(healthFull);
        guis.add(gui);


//        LIGHT
        Light light = new Light(new Vector3f(-2000,5000, -7000), new Vector3f(1,1,1));
        List<Light> lights = new ArrayList<Light>();
        lights.add(light);
        lights.add(new Light(new Vector3f( 2000, 5000, 7000), new Vector3f(1,1,1)));

//        PLAYER

        TexturedModel car = new TexturedModel(OBJLoader.loadObjModel("CarModel", loader), new ModelTexture(loader.loadTexture("CarTexpng")) );
        Player player = new Player(car, new Vector3f(150, 5, -240), 0, 180, 0, 4.2f);
        Camera camera = new Camera(player);


//        OTHER CARS

//        ModelData car1 = OBJFileLoader.loadOBJ("CarModel");
//        RawModel carModel = loader.loadToVAO(car1.getVertices(), car1.getTextureCoords(), car1.getNormals(), car1.getIndices());
//        ModelTexture carTexture = new ModelTexture(loader.loadTexture("CarTexpng"));
//        TexturedModel otherCar = new TexturedModel(carModel,carTexture);



//


//        =========================================================================

        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();

        for(int i=0; i < 800 ; i++){

            float x = random.nextFloat()* 1600 - 50;
            float z = random.nextFloat()* -1600 + 50;
            while(!(x > 0 && x < 1600 && z < 0 && z > -1600)){
                x = random.nextFloat()* 800 - 200;
                z = random.nextFloat()* -600 + 100;
            }
            if((x > 0 && x < 1070 && z < -1570 && z > -1600) ||
                    (x > 0 && x < 106 && z > -1600 && z < -1117) ||
                    (x > 247 && x < 292 && z < -47 && z > -108) ||
                    (x > 1048 && x < 1079 && z < -847 && z > -976) ||
                    (x > 1070 && x < 1600 && z > -1600 && z < -950) ||
                    (x > 0 && x < 323 && z > -973 && z < -750) ||
                    (x > 530 && x < 1150 && z < 0 && z > -90) ||
                    (x > 1270 && x < 1600 && z < 0 && z > -311) ||
                    (x > 1340 && x < 1600 && z > -880 && z < -675 )) {

                float y = terrain.getHeightOfTerrain(x, z);
                if(random.nextFloat() > 0.5){
                    entities.add(new Entity(tree, new Vector3f(x,y,z),
                            0,0,0, random.nextFloat() *3f +13,1));
                }else {
                    entities.add(new Entity(staticModel, random.nextInt(4), new Vector3f(x, y, z),
                            0, random.nextFloat() * 360, 0, random.nextFloat() * 0.8f + 0.5f,1));
                }
            }
        }


        for(int i = 0; i < 100 ; i++){
            float x = random.nextFloat()* 1600 - 200;
            float z = random.nextFloat()* -1600 + 200;
            while(!(x > 0 && x < 1600 && z < 0 && z > -1600)){
                x = random.nextFloat()* 800 - 300;
                z = random.nextFloat()* -600 + 200;
            }

            if((x > 209 && x < 558 && z < -1144 && z > -1286) ||
                    (x > 620 && x < 913 && z < -220 && z > -1311) ||
                    (x > 314 && x < 1106 && z < -390 && z > -532)){
                float y = terrain.getHeightOfTerrain(x, z);
                int number = random.nextInt(4);
                switch (number){
                    case 0:
                        entities.add(new Entity(house1, new Vector3f(x,y,z),
                                0, 0, 0, 15,2));
                        break;
                    case 1:
                        entities.add(new Entity(house2, new Vector3f(x,y,z),
                                0, 0, 0, 15,3));
                        break;
                    case 2:
                        entities.add(new Entity(house3, new Vector3f(x,y,z),
                                0, 0, 0, 15,4));
                        break;
                    case 3:
                        entities.add(new Entity(house4, new Vector3f(x,y,z),
                                0, 0, 0, 15,5));
                        break;
                    case 4:
                        entities.add(new Entity(house5, new Vector3f(x,y,z),
                                0, 0, 0, 15,6));
                        break;
                }
            }

        }
        //autka ktore jada narazie prosto!!!
        entities.add(new Entity(car,new Vector3f(170,terrain.getHeightOfTerrain(170,-280),-280),0,170,0,4.2f,7));
        entities.add(new Entity(car,new Vector3f(150,terrain.getHeightOfTerrain(150,-330),-330),0,170,0,4.2f,7));
//        entities.add(new Entity(car,new Vector3f(150,terrain.getHeightOfTerrain(150,-290),-290),0,170,0,4.2f,7));
//        entities.add(new Entity(car,new Vector3f(170,terrain.getHeightOfTerrain(170,-320),-320),0,170,0,4.2f,7));




        //ludzik
        entities.add(new Entity(starter, new Vector3f(100,terrain.getHeightOfTerrain(100, -350),-350),0,0,0,1.0f, 0));

        boolean killed = false;
        boolean z = true;
        boolean x = true;
        boolean z5 = true;
        boolean x5 = true;
        boolean left = true;
        MasterRenderer renderer = new MasterRenderer(loader);
        GuiRenderer guiRenderer = new GuiRenderer(loader);
//        ====================================================

        while (window.isRunning()) {

            camera.move();
            player.move(terrain);

            renderer.processEntity(player);
            renderer.processTerrain(terrain);

            for (Entity entity : entities) {
                renderer.processEntity(entity);
                if(player.checkCrash(entity)){
                    if(entity.getType() == 0){
                        if(entity.getPosition().x <= 170){
                            entities.remove(entity);
                            entities.add(new Entity(dead, new Vector3f(100,terrain.getHeightOfTerrain(100, -350),-350),90,180,90,1.6f, 0));
                            killed = true;
                        }else{
                            entities.remove(entity);
                            entities.add(new Entity(dead, new Vector3f(210,terrain.getHeightOfTerrain(210, -410),-410),90,180,90,1.6f, 0));
                            killed = true;
                        }
                    }
                    if(player.getEndurance() == 3 ) {
                        if(guis.contains(healthFull)) {
                            guis.remove(healthFull);
                            guis.add(health5);
                        }
                    }else if(player.getEndurance() == 2 ){
                        if(guis.contains(health5)) {
                            guis.remove(health5);
                            guis.add(health3);
                        }
                    }else if(player.getEndurance() == 1 ) {
                        if(guis.contains(health3)){
                            guis.remove(health3);
                            guis.add(health1);
                        }
                    }
                }
            }
            if(!killed) {
                entities.get(entities.size() - 1).setRotY(entities.get(entities.size() - 1).getRotY() + random.nextInt(2));

                if(left){
                    entities.get(entities.size() - 1).setPosition(new Vector3f(entities.get(entities.size() - 1).getPosition().x + 0.2f,
                            terrain.getHeightOfTerrain(entities.get(entities.size() - 1).getPosition().x + 0.2f, entities.get(entities.size() - 1).getPosition().z)
                            , entities.get(entities.size() - 1).getPosition().z));
                    if(entities.get(entities.size() - 1).getPosition().x >290){
                        left = false;
                    }
                }else{
                    entities.get(entities.size() - 1).setPosition(new Vector3f(entities.get(entities.size() - 1).getPosition().x - 0.2f,
                            terrain.getHeightOfTerrain(entities.get(entities.size() - 1).getPosition().x - 0.2f, entities.get(entities.size() - 1).getPosition().z)
                            , entities.get(entities.size() - 1).getPosition().z));
                    if(entities.get(entities.size() - 1).getPosition().x <100){
                        left = true;
                    }
                }
            }
//          AUTKA TU SOBIE JADA
//            entities.get(entities.size() - 2).setPosition(new Vector3f(entities.get(entities.size() - 2).getPosition().x + 0.2f,
//                    terrain.getHeightOfTerrain(entities.get(entities.size() - 2).getPosition().x + 0.2f, entities.get(entities.size() - 2).getPosition().z)
//                    , entities.get(entities.size() - 2).getPosition().z-1.0f));
//
//            entities.get(entities.size() - 3).setPosition(new Vector3f(entities.get(entities.size() - 3).getPosition().x + 0.2f,
//                    terrain.getHeightOfTerrain(entities.get(entities.size() - 3).getPosition().x + 0.2f, entities.get(entities.size() - 3).getPosition().z)
//                    , entities.get(entities.size() - 3).getPosition().z-1.0f));
//
//            entities.get(entities.size() - 4).setPosition(new Vector3f(entities.get(entities.size() - 4).getPosition().x + 0.2f,
//                    terrain.getHeightOfTerrain(entities.get(entities.size() - 4).getPosition().x + 0.2f, entities.get(entities.size() - 4).getPosition().z)
//                    , entities.get(entities.size() - 4).getPosition().z-1.0f));


            //AUTKO JEZDZI SOBIE PO KWADRACIE

            if(entities.get(entities.size() - 2).getPosition().z > -1400 && z && x) {

                entities.get(entities.size() - 2).setPosition(new Vector3f(entities.get(entities.size() - 2).getPosition().x,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 2).getPosition().x, entities.get(entities.size() - 2).getPosition().z - 1.0f)
                        , entities.get(entities.size() - 2).getPosition().z - 1.0f));
                if (entities.get(entities.size() - 2).getPosition().z <= -1400) {
                    entities.get(entities.size() - 2).setRotY(entities.get(entities.size() - 2).getRotY() - 100);
                    z = false;
                }
            }


            else if(entities.get(entities.size() - 2).getPosition().z <= -1400 && entities.get(entities.size() - 2).getPosition().x < 1400 && x){
                entities.get(entities.size() - 2).setPosition(new Vector3f(entities.get(entities.size() - 2).getPosition().x+1.0f,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 2).getPosition().x+1.0f, entities.get(entities.size() - 2).getPosition().z)
                        , entities.get(entities.size() - 2).getPosition().z));

                if(entities.get(entities.size() - 2).getPosition().z <= -1400 && entities.get(entities.size() - 2).getPosition().x >= 1400){
                    entities.get(entities.size() - 2).setRotY(entities.get(entities.size() - 2).getRotY() - 100);
                    x = false;
                }
            }

           else if(entities.get(entities.size() - 2).getPosition().z <= -330 && entities.get(entities.size() - 2).getPosition().x >= 1400){

                entities.get(entities.size() - 2).setPosition(new Vector3f(entities.get(entities.size() - 2).getPosition().x,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 2).getPosition().x, entities.get(entities.size() - 2).getPosition().z+1.0f)
                        , entities.get(entities.size() - 2).getPosition().z+1.0f));

                if(entities.get(entities.size() - 2).getPosition().z >= -330 && entities.get(entities.size() - 2).getPosition().x >= 1400){
                    entities.get(entities.size() - 2).setRotY(entities.get(entities.size() - 2).getRotY() - 100);
                    z = true;
                }
            }

           else if(entities.get(entities.size() - 2).getPosition().z >= -330 && entities.get(entities.size() - 2).getPosition().x >= 150){

                entities.get(entities.size() - 2).setPosition(new Vector3f(entities.get(entities.size() - 2).getPosition().x-1.0f,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 2).getPosition().x-1.0f, entities.get(entities.size() - 2).getPosition().z)
                        , entities.get(entities.size() - 2).getPosition().z));

                if(entities.get(entities.size() - 2).getPosition().z >= -330 && entities.get(entities.size() - 2).getPosition().x <= 150) {
                    entities.get(entities.size() - 2).setRotY(entities.get(entities.size() - 1).getRotY() - 100);
                    x = true;
                }
            }


           //AUTKO NR 2

            if(entities.get(entities.size() - 3).getPosition().z > -1400 && z5 && x5) {

                entities.get(entities.size() - 3).setPosition(new Vector3f(entities.get(entities.size() - 3).getPosition().x,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 3).getPosition().x, entities.get(entities.size() - 3).getPosition().z - 1.0f)
                        , entities.get(entities.size() - 3).getPosition().z - 1.0f));
                if (entities.get(entities.size() - 3).getPosition().z <= -1400) {
                    entities.get(entities.size() - 3).setRotY(entities.get(entities.size() - 3).getRotY() - 100);
                    z5 = false;
                }
            }


            else if(entities.get(entities.size() - 3).getPosition().z <= -1400 && entities.get(entities.size() - 3).getPosition().x < 1400 && x5){
                entities.get(entities.size() - 3).setPosition(new Vector3f(entities.get(entities.size() - 3).getPosition().x+1.0f,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 3).getPosition().x+1.0f, entities.get(entities.size() - 3).getPosition().z)
                        , entities.get(entities.size() - 3).getPosition().z));

                if(entities.get(entities.size() - 3).getPosition().z <= -1400 && entities.get(entities.size() - 3).getPosition().x >= 1400){
                    entities.get(entities.size() - 3).setRotY(entities.get(entities.size() - 3).getRotY() - 100);
                    x5 = false;
                }
            }

            else if(entities.get(entities.size() - 3).getPosition().z <= -330 && entities.get(entities.size() - 3).getPosition().x >= 1400){

                entities.get(entities.size() - 3).setPosition(new Vector3f(entities.get(entities.size() - 3).getPosition().x,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 3).getPosition().x, entities.get(entities.size() - 3).getPosition().z+1.0f)
                        , entities.get(entities.size() - 3).getPosition().z+1.0f));

                if(entities.get(entities.size() - 3).getPosition().z >= -330 && entities.get(entities.size() - 3).getPosition().x >= 1400){
                    entities.get(entities.size() - 3).setRotY(entities.get(entities.size() - 3).getRotY() - 100);
                    z5 = true;
                }
            }

            else if(entities.get(entities.size() - 3).getPosition().z >= -330 && entities.get(entities.size() - 3).getPosition().x >= 150){

                entities.get(entities.size() - 3).setPosition(new Vector3f(entities.get(entities.size() - 3).getPosition().x-1.0f,
                        terrain.getHeightOfTerrain(entities.get(entities.size() - 3).getPosition().x-1.0f, entities.get(entities.size() - 3).getPosition().z)
                        , entities.get(entities.size() - 3).getPosition().z));

                if(entities.get(entities.size() - 3).getPosition().z >= -330 && entities.get(entities.size() - 3).getPosition().x <= 150) {
                    entities.get(entities.size() - 3).setRotY(entities.get(entities.size() - 3).getRotY() - 100);
                    x5 = true;
                }
            }





            //entities.get(entities.size() - 5).setRotY(entities.get(entities.size() - 1).getRotY() + random.nextInt(2));



            renderer.render(lights, camera);
            if(player.isGameOver()){
                System.out.println("Game over!");
                break;

            }

            guiRenderer.render(guis);
            window.updateWindow();
        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        window.destroy();
    }

}
