import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.font.GraphicAttribute;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;



public class miniMusicPlayer {

    static JFrame frame = new JFrame("Mini-MusicPlayer");
    static myDrawPanel drawPanel;

    // main function
    public static void main(String[] args){
        miniMusicPlayer mini = new miniMusicPlayer();
        mini.play();
    } // close main




    public void setUpGui(){

        drawPanel = new myDrawPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setVisible(true);

    }




    public void play(){

        // !Set up miniMusicPlayer GUI...
        setUpGui();

        // !Play Music...
        try {

            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(drawPanel, new int[] {127});

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            int note = 0;
            int num = 60;
            for (int indx=0; indx<num; indx+=4){
                note = (int) (Math.random()*50 + 1);

                track.add(makeEvent(144, 1, note, 100, indx));
                track.add(makeEvent(176, 1, 127, 100, indx));
                track.add(makeEvent(128, 1, note, 100, indx+2));

            } // close for

            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(120);
            sequencer.start();

        } catch (Exception ex) { ex.printStackTrace(); }

    } // close play



    public MidiEvent makeEvent(int cmd, int chan, int note, int pow, int tick){

        MidiEvent event = null;

        try {

            ShortMessage msg = new ShortMessage();
            msg.setMessage(cmd, chan, note, pow);
            event = new MidiEvent(msg, tick);

        } catch (Exception ex) {}

        return event;

    } // close makeEvent



    // !inner class...
    class myDrawPanel extends JPanel implements ControllerEventListener {

        boolean msg = false;

        // !Implement the interface of ControllerEventListener...
        public void controlChange(ShortMessage event){
            msg = true;
            frame.repaint();
        }

        public void paintComponent(Graphics g){
            if(msg){

                Graphics2D g2D = (Graphics2D) g;

                // !Set panelColor...
                int red = (int) (Math.random()*255);
                int green = (int) (Math.random()*255);
                int blue = (int) (Math.random()*255);

                g.setColor(new Color(red, green, blue));


                // !Set panelWidth and panelHeight...
                int height = (int) (Math.random()*120 + 10);
                int width = (int) (Math.random()*120 + 10);

                int x = (int) (Math.random()*40 + 10);
                int y = (int) (Math.random()*40 + 10);

                g.fillRect(x, y, height, width);
                msg = false;
            } // close if

        } // close paintComponent

    } // close myDrawPanel






}
