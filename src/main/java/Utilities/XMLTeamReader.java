package Utilities;

import Misc.City;
import Misc.State;
import Misc.Team;
import People.Player;
import People.Player.Position;
import People.Traits;
import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLTeamReader implements XMLReader {
    public static void main(String[] args) {
        String path = "/xml/teams/TestTeam.xml";
        XMLTeamReader test = new XMLTeamReader(path);
    }
    // Field initialization
    private String filePath;
    private FileType fileType;
    private Document doc;
    private List<Player> team = new ArrayList<Player>();
    private String teamName;

    // Constructor
    public XMLTeamReader(String filePath) {
        // Filepath
        this.filePath = (String) System.getProperty("user.dir") + filePath;

        // Build dbf
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // Security stuff
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // Parse document
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.doc = db.parse(new File(this.filePath));
            // Get name of team
            NodeList nameTag = this.doc.getElementsByTagName("team");
            for (int i = 0; i < nameTag.getLength(); i++) {
                Node node = nameTag.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    NamedNodeMap nodeMap = node.getAttributes();
                    this.teamName = node.getAttributes().getNamedItem("name").getNodeValue();
                }
            }
            // Find if it is high school or university
            NodeList schoolType = this.doc.getElementsByTagName("school_type");
            for (int i = 0; i < schoolType.getLength(); i++) {
                Node node = schoolType.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String school = node.getTextContent();
                }
            }

            // Get all players
            NodeList playerList = this.doc.getElementsByTagName("player");

            // Initialize attributes
            double speed = 1; double agility = 1; double acceleration = 1; double strength = 1;
            double jumping = 1; double aggressiveness = 1; double composure = 1; double vision = 1;
            double arm_power = 1; double accuracy = 1; double catching = 1; double kick_power = 1;
            double kick_accuracy = 1; double tackling = 1; double pass_rush = 1; double run_defense = 1;
            double run_block = 1; double pass_block = 1;

            // Initialize list of traits
            List<Traits> traitList = new ArrayList<Traits>();

            // Iterate through players
            for (int i = 0; i < playerList.getLength(); i++) {
                Node node = playerList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Get player ID
                    String playerId = element.getAttribute("id");

                    // Read attributes
                    NodeList attributes = element.getElementsByTagName("attributes");

                    for (int j = 0; j < attributes.getLength(); j++) {
                        // Physical attributes
                        speed = Double.parseDouble(element.getElementsByTagName("speed").item(0).getTextContent());
                        agility = Double.parseDouble(element.getElementsByTagName("agility").item(0).getTextContent());
                        acceleration = Double.parseDouble(element.getElementsByTagName("acceleration").item(0).getTextContent());
                        strength = Double.parseDouble(element.getElementsByTagName("strength").item(0).getTextContent());
                        jumping = Double.parseDouble(element.getElementsByTagName("jumping").item(0).getTextContent());
                        aggressiveness = Double.parseDouble(element.getElementsByTagName("aggressiveness").item(0).getTextContent());

                        // Mentality
                        composure = Double.parseDouble(element.getElementsByTagName("composure").item(0).getTextContent());
                        vision = Double.parseDouble(element.getElementsByTagName("vision").item(0).getTextContent());

                        // QB Stats
                        arm_power = Double.parseDouble(element.getElementsByTagName("arm_power").item(0).getTextContent());
                        accuracy = Double.parseDouble(element.getElementsByTagName("accuracy").item(0).getTextContent());

                        // WR Stats
                        catching = Double.parseDouble(element.getElementsByTagName("catching").item(0).getTextContent());

                        // Kicking Stats
                        kick_power = Double.parseDouble(element.getElementsByTagName("kick_power").item(0).getTextContent());
                        kick_accuracy = Double.parseDouble(element.getElementsByTagName("kick_accuracy").item(0).getTextContent());

                        // Defensive Stats
                        tackling = Double.parseDouble(element.getElementsByTagName("tackling").item(0).getTextContent());
                        pass_rush = Double.parseDouble(element.getElementsByTagName("pass_rush").item(0).getTextContent());
                        run_defense = Double.parseDouble(element.getElementsByTagName("run_defense").item(0).getTextContent());

                        // Blocking Stats
                        run_block = Double.parseDouble(element.getElementsByTagName("run_block").item(0).getTextContent());
                        pass_block = Double.parseDouble(element.getElementsByTagName("pass_block").item(0).getTextContent());
                    }

                    // Read name
                    String playerName = (element.getElementsByTagName("name").item(0).getTextContent());

                    // Read position
                    Position playerPos = Position.valueOf(element.getElementsByTagName("position").item(0).getTextContent());

                    // Read home city
                    State homeState = new State((element.getElementsByTagName("state").item(0).getTextContent()));
                    City homeCity = new City((element.getElementsByTagName("city").item(0).getTextContent()), homeState);

                    // Read age
                    int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());

                    // Read traits
                    NodeList traits = element.getElementsByTagName("trait");
                    for (int j = 0; j < traits.getLength(); j++) {
                        Node trait = traits.item(j);
                        if (trait.getNodeType() == Node.ELEMENT_NODE) {
                            String currentTrait = trait.getTextContent();
                            try {
                                String traitString = currentTrait.replaceAll("\\s", "").toUpperCase();
                                Traits cto = Traits.valueOf(traitString);
                                traitList.add(cto);
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }

                    // Create tmp player
                    Player tmpPlayer = new Player(playerName, age, null, homeCity, playerPos);

                    // Add attributes to player
                    tmpPlayer.setAggressiveness(aggressiveness); tmpPlayer.setComposure(composure);
                    tmpPlayer.setVision(vision); tmpPlayer.setAcceleration(acceleration);
                    tmpPlayer.setAccuracy(accuracy); tmpPlayer.setKick_accuracy(kick_accuracy);
                    tmpPlayer.setAgility(agility); tmpPlayer.setArm_power(arm_power);
                    tmpPlayer.setSpeed(speed); tmpPlayer.setStrength(strength); tmpPlayer.setJumping(jumping);
                    tmpPlayer.setKick_power(kick_power); tmpPlayer.setCatching(catching);
                    tmpPlayer.setTackling(tackling); tmpPlayer.setRun_defense(run_defense);
                    tmpPlayer.setPass_block(pass_block); tmpPlayer.setRun_block(run_block); tmpPlayer.setPass_rush(pass_rush);

                    // Set traits
                    tmpPlayer.addTrait(traitList);

                    // Add player to team
                    this.team.add(tmpPlayer);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFilepath() {
        return this.filePath;
    }

    @Override
    public FileType getFiletype() {
        return FileType.Team;
    }

    public List<Player> getTeam() {
        return this.team;
    }

    public Document getDoc() {
        return this.doc;
    }
}
