import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public calss FloreTownFinal1 estands JFrame implements ActionListener
{
    JLabel title, info, timerLable1, scoreLalbe1, lederboardLabel;
    JTextField input, nameField;
    JButton guessBtn, easyBtn, mediumBtn, hardBtn, startBtn;

    int number, attempts, timeLeft, score;
     javax.swing.Timer timer;
     Random rand = new Random();

     java.util.List<String> leaderboard = new Arraylist<>();
     String playerName = "player";

     public FloreTownFinal1()
     {
        setTitle("FloreTown Guess Game");
        setSize(520, 600);
        setLayout(new BorderLayout(15,15));
        getContentPane().setBackground(new color(20,20,30));

        // ===== TITLE =====
        title = new JLabel("FloreTown Guess Game", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.Bold));
        title.setForeground(new Color(0, 200, 255));

        // ===== TOP PANEL =====
        JPanel top = new JPanel(new GridLayout(5,1,8,8));
        top.setBackground(new Color(20,20,30));

        nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        nameField.setBorder(BorderFactory.createTitledBorder("Enter Name"));

        starBtn = createButton("Start Game");

        info = new JLabel("Choose Difficulty", JLabel.CENTER);
        info.setForeground(color.WHITE);

        timeLabel1 = new JLabel("time: 0", JLabel.CENTER);
        timeLabel1.setForeground(Color.ORANGE);

        top.add(title);
        top.add(nameField);
        top.add(startBtn);
        top.add(info);
        top.add(timerLable1);

        // ===== CENTER PANEL =====
        JLabel center = new JLabel(new GridLayout(3,1,10,10));
        center.setBackground(new Color(20,20,30));

        input = new JTextField();
        input.setFont(new Font("Segoe UI", Font.BOLD, 18));
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setBorder(BorderFactory.createTitledBorder("Enter Guess")):

        guessBtn = createButton("Guess");

        scoreLalbe1 = new JLabel("Score: 0", JLabel.CENTER);
        scoreLalbe1.setForeground(new Color(0, 255, 150));

        center.add(input);
        center.add(guessBtn);
        center.add(scoreLalbe1);

        // ===== BOTTOM PANEL =====
        JPanel bottom = new JPanel(new GridLayout(1,3,10,10));
        bottom.setBackground(new Color(20,20,30));

        easyBtn = createButton("Easy");
        mediumBtn = createButton("Medium");
        hardBtn = createButton("Hard");

        bottom.add(easyBtn);
        bottom.add(mediumBtn);
        bottom.add(hardBtn);

        // ===== LEADERBOARD =====
        leaderboardLabel = new JLabel("Leaderboard", JLabel.CENTER);
        leaderboardLabel.setForeground(Color.PINK);
        
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        add(leaderboardLabel, BorderLayout.EAST);

        // EVENTS
        guessBtn.addActionListener(this);
        easyBtn.addActionListener(this);
        mediumBtn.addActionListener(this);
        hardBtn.addActionListener(this);
        startBtn.addActionListener(this);

        loadLeaderboard();
        updateLeaderboard();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Stylish Button
    JButton createButton(String text)
   {
       JButton btn = new JButton(text);
       btn.setFont(new Font("Segoe UI", Font.BOLD,14));
       btn.setBackground(new Color(40,40,60));
       btn.setForeground(Color.WHITE);
       btn.setFocusPainted(false);

       btn.setBorder(BorderFactory.createLineBorder(Color.CYAN,1));
       return btn;
    }

    void startGame(int range, int time)
    {
        number = rand.nextInt(range) + 1;
        attempts = 0;
        score = 100;
        timeLeft = time;

        info.setText("Guess between 1 - " + range);

        timer = new javax.swing.Timer(1000, e ->{
        timeLeft--;
        timerLable1.setText("Time:" + timeLeft);

        if (timeleff <=0)
        {
            timer.stop();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Time's up! Number: " + number); 
          }
          });
          timer.start();
        }

        public void actionPerformed(ActionEvente)
        [ 
           if (e.getSource() == startBtn)
           {
            playerName = nameField.getText().trim();
            if (playerName.equals("")) playerName = "Player";
            JOptionPane.showMessageDialog(this, "Welcome " + playerName);
           }

           if (e.getSource() == easyBtn) startGame(50, 30);
           if (e.getSource() == mediumBtn) startGame(100, 25);
           if (e.getSource() == hardBtn) startGame(200, 20);

           if (e.getSource() == guessBtn)
           {
              try {
                  int guess = Integer.parseInt(input.getText());
                  attempts++;

                  if (guess < number) info.setText("Too Low");
                  else if (guess > number) info.setText("Too High");
                  else {
                           timer.stop();

                            // 🔔 WIN SOUND
                    Toolkit.getDefaultToolkit().beep();
                    Toolkit.getDefaultToolkit().beep();

                    score = Math.max(0, score - attempts * 5);

                    JOptionPane.showMessageDialog(this,
                        "🎉 Correct!\nScore: " + score);

                    leaderboard.add(playerName + " - " + score);
                    saveLeaderboard();
                    updateLeaderboard();
                }

                scoreLabel.setText("⭐ Score: " + score);
                input.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid number!");
            }
        }
    }

    void loadLeaderboard()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("leaderboard.txt"));
            String line;
            while ((line = br.readLine()) != null)
                leaderboard.add(line);
            br.close();
        } catch (Exception e) {}
    }

    void saveLeaderboard()
    {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("leaderboard.txt"));
            for (String s : leaderboard)
                bw.write(s + "\n");
            bw.close();
        } catch (Exception e) {}
    }

    void updateLeaderboard()
    {
        String text = "<html><b>🏆 Leaderboard</b><br>";
        for (String s : leaderboard)
            text += s + "<br>";
        text += "</html>";

        leaderboardLabel.setText(text);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new FloreTownFinal());
    }
}


                  
                   
        


