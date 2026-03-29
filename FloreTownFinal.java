import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class FloreTownFinal extends JFrame implements ActionListener
{
    JLabel title, info, timerLabel, scoreLabel, leaderboardLabel;
    JTextField input, nameField;
    JButton guessBtn, easyBtn, mediumBtn, hardBtn, startBtn;

    int number, attempts, timeLeft, score;
    javax.swing.Timer timer;
    boolean gameOver = false;

    Random rand = new Random();
    java.util.List<String> leaderboard = new ArrayList<>();
    String playerName = "Player";

    public FloreTownFinal()
    {
        setTitle("FloreTown Guess Game");
        setSize(520, 600);
        setLayout(new BorderLayout(15,15));
        getContentPane().setBackground(new Color(20,20,30));

        title = new JLabel("🎮 FloreTown Guess Game", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.CYAN);

        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createTitledBorder("Enter Name"));

        startBtn = new JButton("Start Game");

        info = new JLabel("Choose Difficulty", JLabel.CENTER);
        info.setForeground(Color.WHITE);

        timerLabel = new JLabel("⏱ Time: 0", JLabel.CENTER);
        timerLabel.setForeground(Color.ORANGE);

        scoreLabel = new JLabel("⭐ Score: 0", JLabel.CENTER);
        scoreLabel.setForeground(Color.GREEN);

        JPanel top = new JPanel(new GridLayout(5,1,8,8));
        top.setBackground(new Color(20,20,30));
        top.add(title);
        top.add(nameField);
        top.add(startBtn);
        top.add(info);
        top.add(timerLabel);

        input = new JTextField();
        input.setFont(new Font("Arial", Font.BOLD, 18));
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setBorder(BorderFactory.createTitledBorder("Enter Guess"));

        guessBtn = new JButton("Guess");

        JPanel center = new JPanel(new GridLayout(3,1,10,10));
        center.setBackground(new Color(20,20,30));
        center.add(input);
        center.add(guessBtn);
        center.add(scoreLabel);

        easyBtn = new JButton("Easy");
        mediumBtn = new JButton("Medium");
        hardBtn = new JButton("Hard");

        JPanel bottom = new JPanel(new GridLayout(1,3,10,10));
        bottom.add(easyBtn);
        bottom.add(mediumBtn);
        bottom.add(hardBtn);

        leaderboardLabel = new JLabel("🏆 Leaderboard", JLabel.CENTER);
        leaderboardLabel.setForeground(Color.PINK);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        add(leaderboardLabel, BorderLayout.EAST);

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

    void startGame(int range, int time)
    {
        number = rand.nextInt(range) + 1;
        attempts = 0;
        score = 100;
        timeLeft = time;
        gameOver = false;

        info.setText("Guess between 1 - " + range);
        scoreLabel.setText("⭐ Score: " + score);

        if (timer != null) timer.stop();

        timer = new javax.swing.Timer(1000, e -> {
            if (gameOver) return;

            timeLeft--;
            timerLabel.setText("⏱ Time: " + timeLeft);

            if (timeLeft <= 0)
            {
                gameOver = true;
                timer.stop();

                Toolkit.getDefaultToolkit().beep();

                JOptionPane.showMessageDialog(this,
                    "⏰ Time's up!\nCorrect answer was: " + number);
            }
        });
        timer.start();
    }

    public void actionPerformed(ActionEvent e)
    {
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
            if (gameOver) return;

            try {
                int guess = Integer.parseInt(input.getText());
                attempts++;

                if (guess < number)
                    info.setText("Too Low ⬇️");
                else if (guess > number)
                    info.setText("Too High ⬆️");
                else
                {
                    gameOver = true;
                    timer.stop();

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