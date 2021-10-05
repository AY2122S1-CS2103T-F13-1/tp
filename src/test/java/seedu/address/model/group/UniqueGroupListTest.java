package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroups.AMSTERDAM;
import static seedu.address.testutil.TypicalGroups.BALI;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.testutil.GroupBuilder;

public class UniqueGroupListTest {
    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.contains(null));
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(BALI));
    }

    @Test
    public void contains_groupInList_returnsTrue() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        assertTrue(uniqueGroupList.contains(BALI));
    }

    @Test
    public void contains_groupWithSameIdentityFieldsInList_returnsTrue() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        Group editedBali = new GroupBuilder(BALI).withMembers(IDA, HOON).build();
        assertTrue(uniqueGroupList.contains(editedBali));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.add(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.add(BALI));
    }

    @Test
    public void setGroup_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(null, BALI));
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(BALI, null));
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> uniqueGroupList.setGroup(BALI, BALI));
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        uniqueGroupList.setGroup(BALI, BALI);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(BALI);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasSameIdentity_success() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        Group editedBali = new GroupBuilder(BALI).withTags("Business").build();
        uniqueGroupList.setGroup(BALI, editedBali);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(editedBali);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        uniqueGroupList.setGroup(BALI, AMSTERDAM);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(AMSTERDAM);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException()
            throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        uniqueGroupList.add(AMSTERDAM);
        assertThrows(DuplicateGroupException.class, () -> uniqueGroupList.setGroup(BALI, AMSTERDAM));
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.remove(null));
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> uniqueGroupList.remove(BALI));
    }

    @Test
    public void remove_existingGroup_removesGroup() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        uniqueGroupList.remove(BALI);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullUniqueGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((UniqueGroupList) null));
    }

    @Test
    public void setGroups_uniqueGroupList_replacesOwnListWithProvidedUniqueGroupsList() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(AMSTERDAM);
        uniqueGroupList.setGroups(expectedUniqueGroupList);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroups((List<Group>) null));
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() throws DuplicateGroupException {
        uniqueGroupList.add(BALI);
        List<Group> groupList = Collections.singletonList(AMSTERDAM);
        uniqueGroupList.setGroups(groupList);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        expectedUniqueGroupList.add(AMSTERDAM);
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueGroupList.asUnmodifiableObservableList().remove(0));
    }
}
