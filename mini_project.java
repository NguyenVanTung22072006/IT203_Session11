import java.util.ArrayList;
import java.util.List;

interface ISkill {
    void useUltimate(GameCharacter target);
}

abstract class GameCharacter {
    protected String name;
    protected int hp;
    protected int attackPower;
    protected static int count = 0;
    public GameCharacter(String name, int hp, int attackPower) {
        this.name = name;
        this.hp = hp;
        this.attackPower = attackPower;
        count++;
    }

    public String getName() {
        return name;
    }
    public int getHp() {
        return hp;
    }
    public abstract void attack(GameCharacter target);
    public void takeDamage(int amount) {
        hp -= amount;
        if (hp <= 0) {
            hp = 0;
            System.out.println(name + " đã bị hạ gục!");
        }
    }
    public void displayInfo() {
        System.out.println("Tên: " + name + " | HP: " + hp);
    }
}

class Warrior extends GameCharacter implements ISkill {
    private int armor;
    public Warrior(String name, int hp, int attackPower, int armor) {
        super(name, hp, attackPower);
        this.armor = armor;
    }
    @Override
    public void attack(GameCharacter target) {
        System.out.println("[Chiến binh] " + name + " tấn công " + target.getName() + "!");
        target.takeDamage(attackPower);
    }
    @Override
    public void useUltimate(GameCharacter target) {
        System.out.println("[Chiến binh] " + name + " dùng chiêu cuối ĐẤM NGÀN CÂN!");
        int damage = attackPower * 2;
        target.takeDamage(damage);

        int selfDamage = (int) (hp * 0.1);
        hp -= selfDamage;
        System.out.println(name + " mất " + selfDamage + " HP do gắng sức.");
    }
    @Override
    public void takeDamage(int amount) {
        int realDamage = amount - armor;
        if (realDamage < 0) realDamage = 0;
        super.takeDamage(realDamage);
    }
    @Override
    public void displayInfo() {
        System.out.println("Tên: " + name + " | HP: " + hp + " | Giáp: " + armor);
    }
}
class Mage extends GameCharacter implements ISkill {
    private int mana;
    public Mage(String name, int hp, int attackPower, int mana) {
        super(name, hp, attackPower);
        this.mana = mana;
    }
    @Override
    public void attack(GameCharacter target) {
        int damage;
        if (mana >= 5) {
            mana -= 5;
            damage = attackPower;
            System.out.println("[Pháp sư] " + name + " tung phép!");
        } else {
            damage = attackPower / 2;
            System.out.println("[Pháp sư] " + name + " hết mana, đánh thường!");
        }
        target.takeDamage(damage);
    }
    @Override
    public void useUltimate(GameCharacter target) {
        if (mana >= 50) {
            mana -= 50;
            System.out.println("[Pháp sư] " + name + " tung HỎA CẦU!");
            target.takeDamage(attackPower * 3);
        } else {
            System.out.println(name + " không đủ mana để dùng chiêu cuối!");
        }
    }
    @Override
    public void displayInfo() {
        System.out.println("Tên: " + name + " | HP: " + hp + " | Mana: " + mana);
    }
}
public class mini_project {
    public static void main(String[] args) {
        System.out.println("=== ARENA OF HEROES ===");

        List<GameCharacter> characters = new ArrayList<>();
        Warrior warrior = new Warrior("Yasuo", 500, 50, 20);
        Mage mage = new Mage("Veigar", 300, 40, 200);
        GameCharacter goblin = new GameCharacter("Goblin", 100, 10) {
            @Override
            public void attack(GameCharacter target) {
                System.out.println("[Quái vật] Goblin cắn trộm...");
                target.takeDamage(10);
            }
        };
        characters.add(warrior);
        characters.add(mage);
        characters.add(goblin);
        System.out.println("Đã khởi tạo " + GameCharacter.count + " nhân vật tham gia đấu trường.\n");
        warrior.attack(goblin);
        mage.useUltimate(warrior);
        goblin.attack(mage);
        System.out.println("\n=== THÔNG SỐ SAU LƯỢT ĐẤU ===");
        for (GameCharacter c : characters) {
            c.displayInfo();
        }
    }
}